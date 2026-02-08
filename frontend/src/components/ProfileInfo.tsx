import {useUserContext} from "../context/UserContext.tsx";

function ProfileInfo() {
    const {user} = useUserContext();

    return (
        <section className="shadow-md border-1 border-dark-blue/10 flex flex-col gap-4 items-center justify-center bg-white w-full rounded-xl p-6
            sm:gap-5 sm:p-7
            md:gap-6 md:p-8">

            <div
                className="border-3 border-white/40 rounded-full bg-black/60 w-20 aspect-square flex items-center justify-center text-white text-2xl font-bold
                    sm:w-24 sm:text-3xl
                    md:w-28 md:text-4xl
                    lg:w-1/2"
            >
                {user?.name
                    ?.split(/[\s_]+/)
                    .map(word => word[0]?.toUpperCase())
                    .join('')
                }
            </div>

            <div className="flex flex-col gap-1 text-center
                sm:gap-2">
                <p className="text-xl font-semibold
                    sm:text-2xl
                    md:text-3xl">{user?.name}</p>
                <p className="text-base font-light
                    sm:text-lg
                    md:text-xl">{user?.email}</p>
            </div>

        </section>
    )
}

export default ProfileInfo;