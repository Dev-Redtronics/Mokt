#include <stdarg.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>

/**
 * Struct to hold the decoded data and its length
 */
typedef struct DecodedData {
  uint8_t *data;
  uintptr_t len;
} DecodedData;

/**
 * Retrieves the value of an environment variable
 *
 * # Arguments
 * * `key` - A C-style string pointer to the name of the environment variable
 *
 * # Returns
 * A C-style string pointer to the value of the environment variable,
 * or an empty string if the environment variable is not found
 *
 * # Safety
 * This function performs unsafe operations to convert C strings to Rust strings.
 * It will panic if the key pointer is null or contains invalid UTF-8.
 */
const char *get_env(const char *key);

/**
 * Opens a URL in the default web browser
 *
 * # Arguments
 * * `url` - A C-style string pointer to the URL to open
 *
 * # Safety
 * This function performs unsafe operations to convert C strings to Rust strings.
 * It will panic if the URL pointer is null or contains invalid UTF-8.
 */
void open_url(const char *url);

/**
 * Decodes a Base64 string to a byte array.
 *
 * # Arguments
 *
 * * `encoded` - A C-style string pointer to the Base64 encoded string
 *
 * # Returns
 *
 * * A DecodedData struct containing a pointer to the decoded data and its length
 *
 * # Safety
 * This function performs unsafe operations to convert C strings to Rust strings.
 * It will panic if the encoded pointer is null or contains invalid UTF-8.
 * The caller is responsible for freeing the memory by calling free_decoded_data.
 */
struct DecodedData decode_base64(const char *encoded);

/**
 * Frees memory allocated by decode_base64.
 *
 * # Arguments
 *
 * * `data` - The DecodedData struct returned by decode_base64
 *
 * # Safety
 * This function must be called exactly once for each successful call to decode_base64
 * to avoid memory leaks.
 */
void free_decoded_data(struct DecodedData data);
