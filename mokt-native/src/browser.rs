use std::ffi::CStr;
use std::process::Command;
use libc::c_char;

/// Opens a URL in the default web browser
/// 
/// # Arguments
/// * `url` - A C-style string pointer to the URL to open
/// 
/// # Safety
/// This function performs unsafe operations to convert C strings to Rust strings.
/// It will panic if the URL pointer is null or contains invalid UTF-8.
#[no_mangle]
pub extern "C" fn open_url(url: *const c_char) {
    // Convert C string to Rust string, with safety checks
    let url_str = unsafe {
        // Ensure the pointer is not null
        assert!(!url.is_null(), "URL pointer must not be null");

        // Convert C string to Rust CStr
        CStr::from_ptr(url)
    } 
    .to_str()
    .expect("URL contains invalid UTF-8");

    // Open URL based on the operating system
    match () {
        // Windows: use "cmd /C start <url>"
        _ if cfg!(target_os = "windows") => {
            Command::new("cmd")
                .args(["/C", "start", url_str])
                .spawn()
                .expect("Failed to open URL on Windows");
        }

        // macOS: use "open <url>"
        _ if cfg!(target_os = "macos") => {
            Command::new("open")
                .arg(url_str)
                .spawn()
                .expect("Failed to open URL on macOS");
        }

        // Linux and other platforms: use "xdg-open <url>"
        _ => {
            Command::new("xdg-open")
                .arg(url_str)
                .spawn()
                .expect("Failed to open URL on Linux/Unix");
        }
    }
}
