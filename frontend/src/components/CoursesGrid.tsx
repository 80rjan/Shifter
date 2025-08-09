import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";
import CourseCard from "./CourseCard.tsx";
import {InputAdornment, TextField} from '@mui/material';
import {Search} from 'lucide-react';
import type {FilterParams} from "../models/FilterParams.tsx";
import React from "react";
import {X} from 'lucide-react';
import {queryToDurationMapper, queryToPriceMapper} from "../utils/mapper.ts";

function CoursesGrid({courses, setFilters, filters}: {
    courses: CoursePreview[] | null,
    setFilters: React.Dispatch<React.SetStateAction<FilterParams>>,
    filters: FilterParams,
}) {
    const [searchText, setSearchText] = React.useState<string>("");
    const filterPillClassName = "group hover:border-shifter hover:bg-shifter/10 hover:text-shifter " +
        "flex items-center gap-1 border-1 border-black/40 rounded-full px-4 py-1 text-black/80 font-medium cursor-pointer"
    const filterXClassName = "group-hover:text-shifter text-black/60"

    const handleSearchFilter = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        setFilters(prevFilters => ({
            ...prevFilters,
            search: prevFilters.search?.includes(searchText) ?
                prevFilters.search.filter((v: string) => v !== searchText) :
                [...(prevFilters.search || []), searchText]
        }));

        setSearchText("");
    }


    return (
        <section className="flex flex-col gap-8 py-6 pb-12 w-full">

            {/*SEARCH AND SORT BY*/}
            <div className="flex justify-between items-center w-full px-12">
                {/*Filters*/}
                <div className="flex gap-2 flex-wrap items-center justify-start">
                    {
                        Object.entries(filters).map(([key, values]) => {
                            // Favorite courses filter
                            if (key === "showOnlyFavoriteCourses") {
                                const boolValue = values as boolean;
                                if (!boolValue) return null;
                                return (
                                    <button
                                        onClick={() => setFilters(prev => ({
                                            ...prev,
                                            showOnlyFavoriteCourses: false
                                        }))}
                                        className={filterPillClassName}
                                    >
                                        My Favorites
                                        <X size={20} className={filterXClassName}/>
                                    </button>
                                )
                            }

                            // Array-type filters (topics, skills, difficulties, etc.)
                            return values.map((element: string, idx: number) => (
                                <button
                                    key={`${key}-${idx}`}
                                    onClick={() => {
                                        const updatedArray = values.filter((_: string, i: number) => i !== idx);
                                        const typedKey = key as Exclude<keyof FilterParams, "search">;

                                        setFilters(prev => {
                                            const newParams = {...prev};
                                            if (updatedArray.length === 0) {
                                                delete newParams[typedKey]; // remove key if empty array
                                            } else {
                                                newParams[typedKey] = updatedArray; // otherwise set updated array
                                            }
                                            return newParams;
                                        });
                                    }}
                                    className={filterPillClassName}
                                >
                                    {
                                        key === "price" ?
                                            queryToPriceMapper(element)
                                                .replace(/_/g, " ")
                                                .toLowerCase()
                                                .replace(/\b\w/g, c => c.toUpperCase()) :
                                            key === "duration" ?
                                                queryToDurationMapper(element)
                                                    .replace(/_/g, " ")
                                                    .toLowerCase()
                                                    .replace(/\b\w/g, c => c.toUpperCase()) :
                                                element
                                                    .replace(/_/g, " ")
                                                    .toLowerCase()
                                                    .replace(/\b\w/g, c => c.toUpperCase())
                                    }
                                    <X size={20} className={filterXClassName}/>
                                </button>
                            ));
                        })
                    }
                </div>

                {/*Search*/}
                <form
                    onSubmit={handleSearchFilter}
                    className="flex justify-between w-fit">
                    <TextField
                        size="small"
                        type="search"
                        placeholder="Search..."
                        variant="outlined"
                        value={searchText}
                        onChange={e => setSearchText(e.target.value)}
                        fullWidth
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    <Search size={20}/>
                                </InputAdornment>
                            )
                        }}
                        sx={{
                            "& .MuiOutlinedInput-root": {
                                "& fieldset": {
                                    borderColor: "gray",
                                },
                                "&:hover fieldset": {
                                    borderColor: "black",
                                },
                                "&.Mui-focused fieldset": {
                                    borderColor: "var(--color-shifter)",
                                },
                            },
                        }}
                    />
                    <button
                        type={"submit"}
                        className="hover:shadow-sm hover:shadow-shifter transition-all duration-200 ease-in-out
                        ml-4 px-8 bg-shifter text-white rounded-sm border-2 border-white/40 cursor-pointer
                    ">Discover
                    </button>
                </form>
            </div>

            {/*COURSES GRID*/}
            <div className="relative grid grid-cols-3 gap-x-4 gap-y-4 w-full h-fit px-12">
                {
                    courses && courses?.length > 0 ?
                        courses.map((course, index) => {
                            return (
                                <CourseCard card={course} key={index}/>
                            )
                        }) :
                        <span className="text-3xl font-normal text-black/60 pl-20 py-8 text-left col-span-full">No results found</span>
                }
            </div>
        </section>
    )
}

export default CoursesGrid;