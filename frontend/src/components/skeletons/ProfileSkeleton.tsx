
function ProfileSkeleton() {
    return (
        <main className="grid grid-cols-4 gap-x-12 py-vertical-lg pt-30 px-horizontal-md bg-beige">
            <div className="col-span-1 flex flex-col gap-6">
                {/* ProfileInfo Skeleton */}
                <section className="border-1 border-dark-blue/10 flex flex-col gap-6 items-center justify-center bg-white w-full rounded-xl p-8">
                    {/* Avatar skeleton */}
                    <div className="border-3 border-white/40 rounded-full bg-gray-300 w-1/2 aspect-square flex items-center justify-center animate-pulse"></div>

                    {/* Name and email skeleton */}
                    <div className="flex flex-col gap-2 w-full items-center">
                        <div className="h-8 bg-gray-300 rounded w-4/5 animate-pulse"></div>
                        <div className="h-6 bg-gray-300 rounded w-full animate-pulse"></div>
                    </div>
                </section>

                {/* ProfileSkillsInterests Skeletons - 3 sections */}
                {["Learned Skills", "Desired Skills", "Interests"].map((_, index) => (
                    <section key={index} className="flex flex-col gap-2 items-start w-full rounded-xl">
                        <div className="h-8 bg-gray-300 rounded w-32 animate-pulse"></div>
                        <div className="flex gap-2 w-full flex-wrap border-1 border-dark-blue/10 bg-white rounded-lg p-4">
                            {/* Skills pills skeleton */}
                            {[...Array(4)].map((_, pillIndex) => (
                                <div key={pillIndex} className="h-8 bg-gray-300 rounded-md w-20 animate-pulse"></div>
                            ))}
                            {/* Show more button skeleton */}
                            <div className="h-8 bg-gray-300 rounded w-16 animate-pulse"></div>
                        </div>
                    </section>
                ))}
            </div>

            <div className="col-span-3">
                {/* ProfileMyProfile Skeleton */}
                <section className="flex flex-col gap-4 items-start">
                    <div className="h-10 bg-gray-300 rounded w-32 animate-pulse"></div>
                    <div className="flex flex-col gap-6 px-24 py-6 border-1 border-dark-blue/10 bg-white rounded-lg w-full">
                        {/* Form inputs skeleton */}
                        {[...Array(3)].map((_, index) => (
                            <div key={index} className="h-12 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                        ))}
                        {/* Submit button skeleton */}
                        <div className="h-10 bg-gray-300 rounded-sm w-32 animate-pulse"></div>
                    </div>
                </section>
            </div>
        </main>
    );
}

export default ProfileSkeleton;