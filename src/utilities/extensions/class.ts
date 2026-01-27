// deno is highly intelligent and believes that any is bad
// while this is (like 99% of the time) true, it also believes
// that Mixin classes need to take an `any` rest parameter.
// It also flags that as any so to bypass this pure genius
// from spamming ignore comments, this shitty hack is used.
// for fuck's sake.
// deno-lint-ignore no-explicit-any
type Any = any;

export function final<T extends { new (...args: Any[]): object }>(
    Target: T,
): T {
    return class Final extends Target {
        constructor(...args: Any[]) {
            if (new.target !== Final) {
                throw new Error("Cannot inherit from a final class!");
            }

            super(...args);
        }
    };
}
