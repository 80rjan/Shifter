import type {CoursePreview} from "../types/CoursePreview.tsx";
import CourseCard from "./CourseCard.tsx";
import {InputAdornment, TextField} from '@mui/material';
import {Search} from 'lucide-react';
import type {FilterParams} from "../types/FilterParams.tsx";
import React from "react";
import {X} from 'lucide-react';
import {queryToDurationMapper, queryToPriceMapper} from "../utils/mapper.ts";
import {useGlobalContext} from "../context/GlobalContext.tsx";


function CoursesGrid({courses, loading, setFilters, filters, showOnlyFavoriteCourses}: {
    courses: CoursePreview[] | null,
    loading: boolean,
    setFilters: React.Dispatch<React.SetStateAction<FilterParams>>,
    filters: FilterParams,
    showOnlyFavoriteCourses: boolean
}) {
    const { user } = useGlobalContext()
    const [searchText, setSearchText] = React.useState<string>(filters.search || "");
    const filterPillClassName = "group hover:border-shifter hover:bg-shifter/10 hover:text-shifter " +
        "flex items-center gap-1 border-1 border-black/40 rounded-full px-4 py-1 text-black/80 font-medium cursor-pointer"
    const filterXClassName = "group-hover:text-shifter text-black/60"

    const handleSearchFilter = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        setFilters(prevParams => ({
            ...prevParams,
            search: searchText.trim()
        }));

        setSearchText("");
    }


    return (
        <section className="flex flex-col gap-8 py-6 pb-12 w-full">

            {/*SEARCH AND SORT BY*/}
            <div className="flex justify-between items-center w-full px-12">
                <div className="flex gap-2 flex-wrap items-center justify-start">
                    {
                        Object.entries(filters).map(([key, values]) => {
                            if (key === "search") {
                                const searchValue = values as string;
                                return (
                                    <button
                                        key={`${key}-${searchValue}`}
                                        onClick={() => {
                                            setFilters(prev => {
                                                const typedKey = key as keyof FilterParams;
                                                const updatedParams = {...prev};
                                                delete updatedParams[typedKey];
                                                return updatedParams;
                                            });
                                        }}
                                        className={filterPillClassName}
                                    >
                                        {searchValue
                                            .toLowerCase()
                                            .replace(/\b\w/g, c => c.toUpperCase())}
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
                                            const newParams = { ...prev };
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
                    (!courses || loading) && (
                        <div className="absolute inset-0 bg-white/60 backdrop-blur-sm
                        flex flex-col gap-2 items-center justify-start z-10 py-40 w-full">
                            <div className="w-12 loader"></div>
                            <span className="text-xl font-semibold text-black/40">Loading...</span>
                        </div>
                    )
                }
                {
                    courses && courses?.length > 0 ?
                        (
                            showOnlyFavoriteCourses ?
                                courses.filter(course => user?.favoriteCourses.includes(course.id)) :
                                courses
                        )?.map((course, index) => {
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