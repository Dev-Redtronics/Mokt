use std::ffi::{c_char, CStr};
use base64::{Engine as _, engine::general_purpose};

/// Struct to hold the decoded data and its length
#[repr(C)]
pub struct DecodedData {
    data: *mut u8,
    len: usize,
}

/// Decodes a Base64 string to a byte array.
///
/// # Arguments
///
/// * `encoded` - A C-style string pointer to the Base64 encoded string
///
/// # Returns
///
/// * A DecodedData struct containing a pointer to the decoded data and its length
///
/// # Safety
/// This function performs unsafe operations to convert C strings to Rust strings.
/// It will panic if the encoded pointer is null or contains invalid UTF-8.
/// The caller is responsible for freeing the memory by calling free_decoded_data.
#[no_mangle]
pub extern "C" fn decode_base64(encoded: *const c_char) -> DecodedData {
    // Convert C string to Rust string, with safety checks
    let encoded_str = unsafe {
        assert!(!encoded.is_null(), "Encoded string pointer must not be null");
        CStr::from_ptr(encoded)
    }
        .to_str()
        .expect("Encoded string contains invalid UTF-8");

    // Decode the Base64 string
    let decoded = general_purpose::STANDARD.decode(encoded_str).unwrap_or_else(|_| Vec::new());

    // Handle empty result case
    if decoded.is_empty() {
        return DecodedData { data: std::ptr::null_mut(), len: 0 };
    }

    // Convert Vec<u8> to a pointer that can be returned to C
    let mut boxed_data = decoded.into_boxed_slice();
    let data_ptr = boxed_data.as_mut_ptr();
    let len = boxed_data.len();

    // Prevent the data from being dropped when this function returns
    std::mem::forget(boxed_data);

    DecodedData { data: data_ptr, len }
}

/// Frees memory allocated by decode_base64.
///
/// # Arguments
///
/// * `data` - The DecodedData struct returned by decode_base64
///
/// # Safety
/// This function must be called exactly once for each successful call to decode_base64
/// to avoid memory leaks.
#[no_mangle]
pub extern "C" fn free_decoded_data(data: DecodedData) {
    if !data.data.is_null() && data.len > 0 {
        unsafe {
            // Reconstruct the Box and let it drop, freeing the memory
            let _ = Box::from_raw(std::slice::from_raw_parts_mut(data.data, data.len));
        }
    }
}