export function hexToRgb(hex: string) {
    // Remove hash if present
    hex = hex.replace(/^#/, "");
    // Parse r,g,b
    const bigint = parseInt(hex, 16);
    const r = (bigint >> 16) & 255;
    const g = (bigint >> 8) & 255;
    const b = bigint & 255;
    return `${r}, ${g}, ${b}`;
}

