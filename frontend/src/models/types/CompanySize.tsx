export const companySizeOptions = [
    "FREELANCE",
    "MICRO",
    "SMALL",
    "MEDIUM",
    "MID_MARKET",
    "ENTERPRISE",
    "OTHER",
] as const;

export type CompanySize = typeof companySizeOptions[number];