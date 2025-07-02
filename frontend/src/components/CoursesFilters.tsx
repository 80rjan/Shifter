import React from "react";
import Checkbox from "@mui/material/Checkbox";
import type {FilterParams} from "../types/FilterParams.tsx";
import {durationToQueryMapper, priceToQueryMapper} from "../utils/mapper.ts";

function CoursesFilters({setParams, params, topics, skills}: {
    setParams: React.Dispatch<React.SetStateAction<FilterParams>>,
    params: FilterParams,
    topics: string[],
    skills: string[]
}) {
    const duration = [
        "< 3 hours",
        "3-6 hours",
        "6-10 hours",
        "10+ hours"
    ]
    const difficulty = [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED",
        "EXPERT"
    ]
    const price = [
        "Free",
        "< $20",
        "$20 - $50",
        "$50+",
    ]

    const handleDifficultyChange = (param: string) => {
        setParams(prevParams => ({
            ...prevParams,
            difficulty: prevParams.difficulty?.includes(param) ?
                prevParams.difficulty.filter((v: string) => v !== param) :
                [...(prevParams.difficulty || []), param]
        }))
    }

    const handleTopicChange = (param: string) => {
        setParams(prevParams => ({
            ...prevParams,
            topic: prevParams.topic?.includes(param) ?
                prevParams.topic.filter((v: string) => v !== param) :
                [...(prevParams.topic || []), param]
        }))
    }

    const handleSkillChange = (param: string) => {
        setParams(prevParams => ({
            ...prevParams,
            skill: prevParams.skill?.includes(param) ?
                prevParams.skill.filter((v: string) => v !== param) :
                [...(prevParams.skill || []), param]
        }))
    }

    const handlePriceChange = (param: string) => {
        setParams(prevParams => ({
            ...prevParams,
            price: prevParams.price?.includes(param) ?
                prevParams.price.filter((v: string) => v !== param) :
                [...(prevParams.price || []), param]
        }))
    }

    const handleDurationChange = (param: string) => {
        setParams(prevParams => ({
            ...prevParams,
            duration: prevParams.duration?.includes(param) ?
                prevParams.duration.filter((v: string) => v !== param) :
                [...(prevParams.duration || []), param]
        }))
    }

    return (
        <aside
            className="flex flex-col gap-8 pl-8 pt-8 text-left sticky top-0 h-screen bg-white border-r-2 border-black/10">
            <h2 className="text-2xl font-medium">Filter by</h2>
            <div className="relative flex flex-col gap-6 pl-4 pr-2 pb-20 overflow-y-auto scrollable">
                <FilterSelect
                    header={"Level"}
                    options={difficulty}
                    handleFilter={handleDifficultyChange}
                    selectedOptions={params.difficulty || []}
                />
                <FilterSelect
                    header={"Topic"}
                    options={topics}
                    handleFilter={handleTopicChange}
                    selectedOptions={params.topic || []}
                />
                <FilterSelect
                    header={"Skill"}
                    options={skills}
                    handleFilter={handleSkillChange}
                    selectedOptions={params.skill || []}
                />
                <FilterSelect
                    header={"Duration"}
                    options={duration}
                    handleFilter={handleDurationChange}
                    selectedOptions={params.duration || []}
                    mapper={durationToQueryMapper}
                />
                <FilterSelect
                    header={"Price"}
                    options={price}
                    handleFilter={handlePriceChange}
                    selectedOptions={params.price || []}
                    mapper={priceToQueryMapper}
                />

            </div>
            <div
                className="pointer-events-none absolute bottom-0 left-0 w-full h-24 bg-gradient-to-t from-white to-transparent"/>
        </aside>
    )
}

function FilterSelect({
                          header,
                          options,
                          handleFilter,
                          selectedOptions,
                          mapper,
                      }: {
    header: string;
    options: string[];
    handleFilter: (value: string) => void;
    selectedOptions: string[];
    mapper?: (option: string) => string;
}) {
    const [showAll, setShowAll] = React.useState(false);
    const [searchTerm, setSearchTerm] = React.useState("");


    // Filter options based on search term
    const filteredOptions = options.filter((option) =>
        option.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Show only first 4 unless showAll is true
    const visibleOptions = showAll ? filteredOptions : filteredOptions.slice(0, 4);

    return (
        <section className="flex flex-col gap-2">
            <h3 className="text-lg font-medium">{header}</h3>
            <form className="flex flex-col gap-0">
                {options.length > 4 && (
                    <input
                        type="search"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder={`Search ${header
                            .toLowerCase()
                            .replace(/_/g, " ")
                            .replace(/\b\w/g, (c) => c.toUpperCase())}`}
                        className="px-4 py-1 border-2 border-black/20 rounded-sm focus:outline-none focus:border-shifter w-3/4 mb-2"
                    />
                )}
                {visibleOptions.map((option, index) => (
                    <label key={index} className="text-lg text-black whitespace-nowrap">
                        <Checkbox
                            checked={selectedOptions.includes(mapper ? mapper(option) : option)}
                            onChange={() => handleFilter(mapper ? mapper(option) : option)}
                            sx={{
                                color: "var(--color-black)",
                                padding: 0,
                                opacity: 0.6,
                                marginRight: 1,
                                "& .MuiSvgIcon-root": {fontSize: 28},
                                "&.Mui-checked": {color: "var(--color-shifter)", opacity: 1},
                            }}
                        />
                        {option
                            .toLowerCase()
                            .replace(/_/g, " ")
                            .replace(/\b\w/g, (c) => c.toUpperCase())}
                    </label>
                ))}
            </form>
            {filteredOptions.length > 4 && (
                <button
                    type="button"
                    onClick={() => setShowAll(!showAll)}
                    className="w-fit underline cursor-pointer hover:bg-dark-blue/10 hover:text-shifter"
                >
                    {showAll ? "Show less" : `Show ${filteredOptions.length - 4} more`}
                </button>
            )}
        </section>
    );
}

export default CoursesFilters;