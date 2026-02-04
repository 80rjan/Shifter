import {fromEnumFormat} from "./toEnumFormat.ts";

export function parseStringToTagReq(str: string) {
    const [id, ...rest] = str.split("_");
    return {
        id: Number(id),
        value: fromEnumFormat(rest.join("_"))
    };
}