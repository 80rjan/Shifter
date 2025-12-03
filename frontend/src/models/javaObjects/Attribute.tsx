import type {AttributeType} from "../types/AttributeType.tsx";
import type {AttributeTranslate} from "./AttributeTranslate.tsx";

export interface Attribute {
    type: AttributeType;
    attributeTranslateList: AttributeTranslate[];
}