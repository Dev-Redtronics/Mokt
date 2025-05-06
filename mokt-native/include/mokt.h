#include <stdarg.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>

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
