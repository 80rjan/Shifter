
export const getLangFromPath = (pathname: string) => {
    const match = pathname.match(/^\/(en|mk)(\/|$)/i);
    return match ? match[1].toLowerCase() : null;
}