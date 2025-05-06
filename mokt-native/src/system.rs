use libc::c_char;
use std::env;
use std::ffi::{CStr, CString};

/// Retrieves the value of an environment variable
///
/// # Arguments
/// * `key` - A C-style string pointer to the name of the environment variable
///
/// # Returns
/// A C-style string pointer to the value of the environment variable,
/// or an empty string if the environment variable is not found
///
/// # Safety
/// This function performs unsafe operations to convert C strings to Rust strings.
/// It will panic if the key pointer is null or contains invalid UTF-8.
#[no_mangle]
pub extern "C" fn get_env(key: *const c_char) -> *const c_char {
    // Convert C string to Rust string, with safety checks
    let key_str = unsafe {
        // Ensure the pointer is not null
        assert!(!key.is_null(), "Environment variable key must not be null");

        // Convert C string to Rust CStr
        CStr::from_ptr(key)
    }
    // Convert to Rust string, will panic on invalid UTF-8
    .to_str()
    .expect("Environment variable key contains invalid UTF-8");

    // Attempt to get the environment variable
    match env::var(key_str) {
        // If found, convert to C string and return
        Ok(value) => {
            let c_value = CString::new(value)
                .expect("Environment variable value contains null bytes");
            Box::into_raw(Box::new(c_value)) as *const c_char
        },
        // If not found, return empty string
        Err(_) => {
            let empty = CString::new("")
                .expect("Failed to create empty string");
            Box::into_raw(Box::new(empty)) as *const c_char
        },
    }
}
