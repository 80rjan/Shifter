import type {TagType} from "../types/TagType.tsx";
import type {TagTranslate} from "./TagTranslate.tsx";

export interface Tag {
    type: TagType;
    tagTranslateList: TagTranslate[];
}