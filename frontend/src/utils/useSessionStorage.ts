export function getFromSessionStorage<T>(key: string): T | null {
    try {
        const item = sessionStorage.getItem(key);
        if (!item) return null;

        return JSON.parse(item) as T;
    } catch (error) {
        console.error(`Failed to load or parse sessionStorage key "${key}":`, error);
        sessionStorage.removeItem(key); // Remove corrupted data
        return null;
    }
}

export function saveToSessionStorage<T>(key: string, value: T): void {
    try {
        sessionStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
        console.error(`Failed to save "${key}" to sessionStorage:`, error);
    }
}

