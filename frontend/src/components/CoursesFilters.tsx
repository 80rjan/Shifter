import React from "react";
import Checkbox from "@mui/material/Checkbox";
import {durationToQueryMapper, priceToQueryMapper} from "../utils/mapper.ts";
import type {FilterParams} from "../types/FilterParams.tsx";
import {useGlobalContext} from "../context/GlobalContext.tsx";

function CoursesFilters({filters, setFilters, topics, skills}: {
    filters: FilterParams,
    setFilters: React.Dispatch<React.SetStateAction<FilterParams>>,
    topics: string[] | null,
    skills: string[] | null,
}) {
    const {user} = useGlobalContext();
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

    const handleDifficultyChange = (filter: string) => {
        setFilters(prevFilters => ({
            ...prevFilters,
            difficulty: prevFilters.difficulty?.includes(filter) ?
                prevFilters.difficulty.filter((v: string) => v !== filter) :
                [...(prevFilters.difficulty || []), filter]
        }))
    }

    const handleTopicChange = (filter: string) => {
        setFilters(prevFilters => ({
            ...prevFilters,
            topic: prevFilters.topic?.includes(filter) ?
                prevFilters.topic.filter((v: string) => v !== filter) :
                [...(prevFilters.topic || []), filter]
        }))
    }

    const handleSkillChange = (filter: string) => {
        setFilters(prevFilters => ({
            ...prevFilters,
            skill: prevFilters.skill?.includes(filter) ?
                prevFilters.skill.filter((v: string) => v !== filter) :
                [...(prevFilters.skill || []), filter]
        }))
    }

    const handlePriceChange = (filter: string) => {
        setFilters(prevFilters => ({
            ...prevFilters,
            price: prevFilters.price?.includes(filter) ?
                prevFilters.price.filter((v: string) => v !== filter) :
                [...(prevFilters.price || []), filter]
        }))
    }

    const handleDurationChange = (filter: string) => {
        setFilters(prevFilters => ({
            ...prevFilters,
            duration: prevFilters.duration?.includes(filter) ?
                prevFilters.duration.filter((v: string) => v !== filter) :
                [...(prevFilters.duration || []), filter]
        }))
    }

    const handleShowOnlyFavoritesChange = () => {
        setFilters(prevFilters => ({
            ...prevFilters,
            showOnlyFavoriteCourses: !prevFilters.showOnlyFavoriteCourses
        }));
    }

    return (
        <aside
            className="flex flex-col gap-8 pl-8 pt-12 text-left sticky top-0 h-screen border-r-2 border-black/10">
            <h2 className="text-2xl font-medium">Filter by</h2>
            <div className="relative flex flex-col gap-12 pl-4 pr-2 pb-20 overflow-y-auto scrollable">
                {
                    user && (
                        <FilterSelect
                            header={"Favorite Courses"}
                            options={["My favorites"]}
                            handleFilter={() => {handleShowOnlyFavoritesChange()}}
                            selectedOptions={filters.showOnlyFavoriteCourses ? ["My favorites"] : []}
                        />
                    )
                }
                <FilterSelect
                    header={"Level"}
                    options={difficulty}
                    handleFilter={handleDifficultyChange}
                    selectedOptions={filters.difficulty || []}
                />
                <FilterSelect
                    header={"Topics"}
                    options={topics}
                    handleFilter={handleTopicChange}
                    selectedOptions={filters.topic || []}
                />
                <FilterSelect
                    header={"Skills"}
                    options={skills}
                    handleFilter={handleSkillChange}
                    selectedOptions={filters.skill || []}
                />
                <FilterSelect
                    header={"Duration"}
                    options={duration}
                    handleFilter={handleDurationChange}
                    selectedOptions={filters.duration || []}
                    mapper={durationToQueryMapper}
                />
                <FilterSelect
                    header={"Price"}
                    options={price}
                    handleFilter={handlePriceChange}
                    selectedOptions={filters.price || []}
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
    options: string[] | null;
    handleFilter: (value: string) => void;
    selectedOptions: string[];
    mapper?: (option: string) => string;
}) {
    const [showAll, setShowAll] = React.useState(false);
    const [searchTerm, setSearchTerm] = React.useState("");


    // Filter options based on search term
    const filteredOptions = options?.filter((option) =>
        option.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Show only first 4 unless showAll is true
    const visibleOptions = showAll ? filteredOptions : filteredOptions?.slice(0, 4);

    return (
        <section className="flex flex-col gap-2 text-md">
            <h3 className="font-medium">{header}</h3>
            <form className="flex flex-col gap-0">
                {options && options.length > 4 && (
                    <input
                        type="search"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder={`Search ${header
                            .toLowerCase()
                            .replace(/_/g, " ")
                            .replace(/\b\w/g, (c) => c.toUpperCase())}`}
                        className="hover:bg-shifter/5 hover:border-shifter hover:placeholder-black
                        px-4 py-2 border-1 border-black/20 rounded-sm focus:outline-none focus:border-shifter mb-2
                        "/>
                )}
                {visibleOptions?.map((option, index) => (
                    <label key={index} className="flex items-center text-black whitespace-nowrap cursor-pointer w-fit">
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
            {filteredOptions && filteredOptions.length > 4 && (
                <button
                    type="button"
                    onClick={() => setShowAll(!showAll)}
                    className="p-2 rounded-sm w-fit underline cursor-pointer hover:bg-shifter/10 hover:text-shifter"
                >
                    {showAll ? "Show less" : `Show ${filteredOptions.length - 4} more`}
                </button>
            )}
        </section>
    );
}

export default CoursesFilters;