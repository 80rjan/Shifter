import {fromEnumFormat} from "./toEnumFormat.ts";

export function parseStringToAttributeReq(str: string) {
    const [id, ...rest] = str.split("_");
    return {
        id: Number(id),
        value: fromEnumFormat(rest.join("_"))
    };
}