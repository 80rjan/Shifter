import type {Course} from "../types/Course.tsx";
import CourseCard from "./CourseCard.tsx";
import {InputAdornment, TextField} from '@mui/material';
import {Search} from 'lucide-react';
import type {FilterParams} from "../types/FilterParams.tsx";
import React from "react";
import {X} from 'lucide-react';
import {queryToDurationMapper, queryToPriceMapper} from "../utils/mapper.ts";


function CoursesGrid({courses, loading, setParams, params}: {
    courses: Course[],
    loading: boolean,
    setParams: React.Dispatch<React.SetStateAction<FilterParams>>,
    params: FilterParams
}) {
    const [searchText, setSearchText] = React.useState<string>(params.search || "");
    const filterClassName = "border-1 border-black/40 rounded-full px-4 text-black/80 "

    const handleSearchFilter = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        setParams(prevParams => ({
            ...prevParams,
            search: searchText.trim()
        }));

        setSearchText("");
    }

    console.log(params)

    return (
        <section className="flex flex-col gap-8 px-12 py-6 w-full">

            {/*SEARCH AND SORT BY*/}
            <div className="flex justify-between items-center w-full">
                <div className="flex gap-2 flex-wrap items-center justify-start">
                    {
                        Object.entries(params).map(([key, values]) => {
                            if (key === "search") {
                                const searchValue = values as string;
                                return (
                                    <div
                                        key={`${key}-${searchValue}`}
                                        className={filterClassName + " flex items-center gap-1"}
                                    >
                                        {searchValue
                                            .toLowerCase()
                                            .replace(/\b\w/g, c => c.toUpperCase())}
                                        <button
                                            onClick={() => {
                                                setParams(prev => {
                                                    const typedKey = key as keyof FilterParams;
                                                    const updatedParams = {...prev};
                                                    delete updatedParams[typedKey];
                                                    return updatedParams;
                                                });
                                            }}
                                            className="ml-1 text-black/60 hover:text-black cursor-pointer"
                                        >
                                            <X size={16}/>
                                        </button>
                                    </div>
                                )
                            }

                            // Array-type filters (topics, skills, difficulties, etc.)
                            return values.map((element: string, idx: number) => (
                                <div
                                    key={`${key}-${idx}`}
                                    className={filterClassName + " flex items-center gap-1"}
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
                                    <button
                                        onClick={() => {
                                            const updatedArray = values.filter((_: string, i: number) => i !== idx);
                                            const typedKey = key as keyof FilterParams;
                                            setParams(prev => {
                                                const newParams = { ...prev };
                                                if (updatedArray.length === 0) {
                                                    delete newParams[typedKey]; // remove key if empty array
                                                } else {
                                                    newParams[typedKey] = updatedArray; // otherwise set updated array
                                                }
                                                return newParams;
                                            });
                                        }}

                                        className="ml-1 text-black/60 hover:text-black cursor-pointer"
                                    >
                                        <X size={16}/>
                                    </button>
                                </div>
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
            <div className="relative grid grid-cols-3 gap-x-4 gap-y-4 w-full h-fit">
                {
                    loading && (
                        <div className="absolute inset-0 bg-white/60 backdrop-blur-sm
                        flex flex-col gap-2 items-center justify-start z-10 py-40">
                            <div className="w-12 loader"></div>
                            <span className="text-xl font-semibold text-black/40">Loading...</span>
                        </div>
                    )
                }
                {
                    courses.map((course, index) => {
                        return (
                            <CourseCard card={course} key={index}/>
                        )
                    })
                }
            </div>
        </section>
    )
}

export default CoursesGrid;