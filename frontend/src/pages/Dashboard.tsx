import DashboardCourses from "../components/DashboardCourses.tsx";

function Dashboard() {

    return (
        <main className="flex gap-4 px-horizontal-md py-vertical-lg">
            <div className="w-3/5">
                <DashboardCourses/>
            </div>

            <div className="w-2/5">

            </div>
        </main>
    )
}

export default Dashboard;