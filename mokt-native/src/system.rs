use libc::c_char;
use std;
use std::env;
use std::ffi::{CStr, CString};

#[no_mangle]
pub extern "C" fn get_env(key: *const c_char) -> *const c_char {
    let key = unsafe { CStr::from_ptr(key).to_str().unwrap() };
    match env::var(key) {
        Ok(value) => Box::into_raw(Box::new(CString::new(value).unwrap())) as *const c_char,
        Err(_) => Box::into_raw(Box::new(CString::new("").unwrap())) as *const c_char,
    }
}