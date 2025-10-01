// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-S2W-White-Transparent.png";
import {Link, useLocation} from "react-router-dom";
import {fromUrlFormat} from "../utils/toUrlFormat.ts";
import NavbarLink from "../components/NavbarLink.tsx";
import StarFilled from "../assets/icons/StarFilled.tsx";
import StarOutline from "../assets/icons/StarOutline.tsx";
import {useState} from "react";
import ModalReviewCourse from "../components/ModalReviewCourse.tsx";

function NavbarLearn({courseRating, markCourseAsRated}: {
    courseRating?: number;
    markCourseAsRated: (rating: number) => void;
}) {
    const [showReviewModal, setShowReviewModal] = useState<boolean>(false);
    const location = useLocation();
    const courseId = location.pathname.split("/")[2] || "";
    const courseTitleEncoded = location.pathname.split("/")[3] || "";
    const courseTitle = fromUrlFormat(courseTitleEncoded)

    return (
        <nav className="flex justify-between items-center bg-black w-full py-2 px-8 border-b-2 border-white/60">
            <div className="flex gap-4 items-center text-xl text-white ">
                <Link to="/">
                    <img src={logo} alt="Shifter Logo" className="h-12"/>
                </Link>
                <div className="w-[1px] bg-white/40 self-stretch my-2"/>
                {
                    courseTitle && <Link to={`/courses/${courseId}/${courseTitleEncoded}`}
                                         className="hover:brightness-80">{courseTitle}</Link>
                }
                {
                    courseRating !== null && courseRating !== undefined  && (
                        <button
                            onClick={() => setShowReviewModal(true)}
                            className="flex gap-1 cursor-pointer hover:brightness-80"
                        >
                            {[1, 2, 3, 4, 5].map((star) => {
                                const StarIcon = courseRating > 0 && star <= courseRating ? StarFilled : StarOutline;
                                return <StarIcon key={star} className="w-4 text-yellow-400" />;
                            })}
                        </button>
                    )
                }
            </div>

            <div className="flex gap-4">
                <NavbarLink className="w-fit text-white"
                            to={"/learn"} label={"Learn"}/>
                <NavbarLink className="w-fit text-white"
                            to={"/"} label={"Return Home"}/>
            </div>

            {
                showReviewModal && (
                    <ModalReviewCourse
                        courseId={+courseId}
                        closeModal={() => setShowReviewModal(false)}
                        markCourseAsRated={markCourseAsRated}
                        isUpdate={true}
                    />
                )
            }
        </nav>
    )
}

export default NavbarLearn;