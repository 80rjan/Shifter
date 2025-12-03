import {useUserContext} from "../context/UserContext.tsx";

function ProfileInfo() {
    const {user} = useUserContext();

    return (
        <section className="shadow-md border-1 border-dark-blue/10 flex flex-col gap-6 items-center justify-center bg-white w-full rounded-xl p-8 ">

            <div
                className="border-3 border-white/40
                rounded-full bg-black/60 w-1/2 aspect-square flex items-center justify-center text-white text-4xl font-bold"
            >
                {user?.name
                    ?.split(/[\s_]+/)
                    .map(word => word[0]?.toUpperCase())
                    .join('')
                }
            </div>

            <div className="flex flex-col gap-2">
                <p className="text-3xl font-semibold">{user?.name}</p>
                <p className="text-xl font-light">{user?.email}</p>
            </div>


        </section>
    )
}

export default ProfileInfo;