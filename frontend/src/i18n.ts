import i18n from "i18next";
import { initReactI18next } from "react-i18next";

// Import all namespaces
import homeEN from "./locales/en/home.json";
import navbarEN from "./locales/en/navbar.json";
import footerEN from "./locales/en/footer.json";
import contactEN from "./locales/en/contact.json";
import freeConsultationEN from "./locales/en/freeConsultation.json";
import mentoringEN from "./locales/en/mentoring.json";
import consultingEN from "./locales/en/consulting.json";
import academiesEN from "./locales/en/academies.json";
import aboutEN from "./locales/en/about.json";
import loginEN from "./locales/en/login.json";
import registerEN from "./locales/en/register.json";
import personalizeEN from "./locales/en/personalize.json";
import coursesEN from "./locales/en/courses.json";

import homeMK from "./locales/mk/home.json";
import navbarMK from "./locales/mk/navbar.json";
import footerMK from "./locales/mk/footer.json";
import contactMK from "./locales/mk/contact.json";
import freeConsultationMK from "./locales/mk/freeConsultation.json";
import mentoringMK from "./locales/mk/mentoring.json";
import consultingMK from "./locales/mk/consulting.json";
import academiesMK from "./locales/mk/academies.json";
import aboutMK from "./locales/mk/about.json";
import loginMK from "./locales/mk/login.json";
import registerMK from "./locales/mk/register.json";
import personalizeMK from './locales/mk/personalize.json';
import coursesMK from "./locales/mk/courses.json";


i18n
    .use(initReactI18next)
    .init({
        lng: "en",
        fallbackLng: "en",
        ns: ["home", "navbar", "footer", "contact", "freeConsultation", "mentoring", "consulting", "academies", "about",
        "login", "register", "personalize", "courses"],
        defaultNS: "home",
        resources: {
            en: {
                home: homeEN,
                navbar: navbarEN,
                footer: footerEN,
                contact: contactEN,
                freeConsultation: freeConsultationEN,
                mentoring: mentoringEN,
                consulting: consultingEN,
                academies: academiesEN,
                about: aboutEN,
                login: loginEN,
                register: registerEN,
                personalize: personalizeEN,
                courses: coursesEN,
            },
            mk: {
                home: homeMK,
                navbar: navbarMK,
                footer: footerMK,
                contact: contactMK,
                freeConsultation: freeConsultationMK,
                mentoring: mentoringMK,
                consulting: consultingMK,
                academies: academiesMK,
                about: aboutMK,
                login: loginMK,
                register: registerMK,
                personalize: personalizeMK,
                courses: coursesMK,
            }
        },
        interpolation: { escapeValue: false },
    });

export default i18n;
