// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-S2W-White-Transparent.png";
import {useLocation} from "react-router-dom";
import {fromUrlFormat} from "../utils/toUrlFormat.ts";
import NavbarLink from "../components/links/NavbarLink.tsx";
import StarFilled from "../assets/icons/StarFilled.tsx";
import StarOutline from "../assets/icons/StarOutline.tsx";
import {useState} from "react";
import ModalReviewCourse from "../components/ModalReviewCourse.tsx";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";
import type {CourseLearn} from "../models/javaObjects/CourseLearn.tsx";

function NavbarLearn({course, markCourseAsRated}: {
    course: CourseLearn,
    markCourseAsRated: (rating: number) => void;
}) {
    const [showReviewModal, setShowReviewModal] = useState<boolean>(false);
    const location = useLocation();
    const courseId = location.pathname.split("/")[3] || "";
    const courseTitleEncoded = location.pathname.split("/")[4] || "";
    const courseTitle = fromUrlFormat(courseTitleEncoded)

    return (
        <nav className="flex justify-between items-center bg-black w-full py-2 px-8 border-b-2 border-white/60">
            <div className="flex gap-4 items-center text-xl text-white ">
                <LocalizedLink to="/">
                    <img src={logo} alt="Shifter Logo" className="h-12"/>
                </LocalizedLink>
                <div className="w-[1px] bg-white/40 self-stretch my-2"/>
                {
                    courseTitle && <LocalizedLink to={`/courses/${courseId}/${courseTitleEncoded}`}
                                         className="hover:brightness-80">{course.titleShort}</LocalizedLink>
                }
                {
                    course.isFinished && (
                        <button
                            onClick={() => setShowReviewModal(true)}
                            className="flex gap-1 cursor-pointer hover:brightness-80"
                        >
                            {[1, 2, 3, 4, 5].map((star) => {
                                const StarIcon = course.rating && star <= course.rating ? StarFilled : StarOutline;
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
                        isUpdate={course.rating !== null}
                    />
                )
            }
        </nav>
    )
}

export default NavbarLearn;