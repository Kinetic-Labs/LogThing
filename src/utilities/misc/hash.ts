export const generate_hash = (string: string) => {
  let hash = 0;

  for (const char of string) {
    hash = (hash << 5) - hash + char.charCodeAt(0);
    hash |= 0;
  }

  return hash;
};
