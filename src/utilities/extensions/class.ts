export function sealed<T extends { new (...args: any[]): object}>(
    Target: T
): T {
    return class Sealed extends Target {
        constructor(...args: any[]) {
            if(new.target !== Sealed) {
                console.warn("Children allowed to inherit not implemented yet...");
                throw new Error("Cannot inherit from sealed class [unless allowed]!");
            }

            super(...args);
        }
    }
}

export function open<T extends { new (...args: any[]): object}>(
    Target: T
): T {
    return class Open extends Target {
        constructor(...args: any[]) {
            if(new.target === Open) {
                throw new Error("Cannot create an open class that is not inherited from! (Hint: mark as @sealed)");
            }

            super(...args);
        }
    }
}

export function mono<T extends { new (...args: any[]): object }>(Target: T): T {
    let instance: object | undefined;

    return class Mono extends Target {
        constructor(...args: any[]) {
            if(instance) {
                throw new Error(
                    'Cannot instantiate a mono class more than once! (hint: remove @mono or use @poly!)'
                );
            }
            super(...args);
            instance = this;
        }
    } as T;
}
