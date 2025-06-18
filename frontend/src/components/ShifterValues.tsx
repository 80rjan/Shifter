function ShifterValues() {
    return (
        <section className="flex px-horizontal py-vertical-md gap-20">
            <div className="flex flex-col gap-8 w-1/2">
                <div className="flex flex-col gap-4 text-left">
                    <h1 className="text-4xl font-regular">Our Core <span className="font-semibold text-shifter">Values</span></h1>
                    <p className="text-lg">
                        Our SHIFT Values define our approach to mentoring and consulting. We focus on Sustainable Results, a
                        Holistic Approach, In-depth Analysis, Fact-based Solutions, and Tailor-made Services—ensuring
                        impactful and lasting success.
                    </p>
                </div>
                <div className="flex gap-2">
                    <div className="flex flex-col text-center gap-2 font-bold text-shifter text-4xl">
                        <span>S</span>
                        <span>H</span>
                        <span>I</span>
                        <span>F</span>
                        <span>T</span>
                    </div>
                    <div className="flex flex-col text-xl items-start">
                        <span className="h-1/5 flex items-end pb-1">ustainable Results</span>
                        <span className="h-1/5 flex items-end pb-1">olistic Approach</span>
                        <span className="h-1/5 flex items-end pb-1">n-depth Analysis</span>
                        <span className="h-1/5 flex items-end pb-1">act Based Solutions</span>
                        <span className="h-1/5 flex items-end pb-1">ailor Made Services</span>
                    </div>
                </div>
                <button className="hover:shadow-shifter/40 hover:shadow-lg  hover:scale-105 transition-all duration-300 ease-in-out cursor-pointer
                    bg-shifter rounded-md border-3 border-black/20 py-2
                    text-white text-xl shadow-md shadow-shifter/20 font-medium w-7/10"
                >Read More</button>
            </div>

            <div className="relative w-1/2 flex items-center justify-start">
                <div className="rounded-full bg-dark-gray/30 w-70 h-70"></div>
                <div className="absolute left-70 top-1/5 rounded-full bg-dark-gray/30 w-35 h-35"></div>
                <div className="absolute left-70 bottom-1/5 rounded-full bg-dark-gray/30 w-35 h-35"></div>
                <div className="absolute right-20 top-1/2 transform -translate-x-1/2 -translate-y-1/2  rounded-full bg-dark-gray/30 w-20 h-20"></div>
                <div className="absolute left-1/2 top-1/10 transform -translate-x-1/2 -translate-y-1/2  rounded-full bg-dark-gray/30 w-20 h-20"></div>
                <div className="absolute left-2/5 -bottom-1/10 transform -translate-x-1/2 -translate-y-1/2  rounded-full bg-dark-gray/30 w-30 h-30"></div>
                <div className="absolute left-1/5 bottom-4 transform -translate-x-1/2  rounded-full bg-dark-gray/30 w-20 h-20"></div>
                <div className="absolute left-2/7 -top-2 transform -translate-x-1/2 rounded-full bg-dark-gray/30 w-30 h-30"></div>
            </div>
        </section>
    )
}

export default ShifterValues;