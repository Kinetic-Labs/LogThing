/**
 * Simple algorithm to generate a hash from a string
 *
 * NOTE: THIS IS NOT CRYPTOGRAPHICALLY SECURE
 *
 * @param string the string to hash
 */
export const generate_hash = (string: string) => {
  let hash = 0;

  for (const char of string) {
    hash = (hash << 5) - hash + char.charCodeAt(0);
    hash |= 0;
  }

  return hash;
};
