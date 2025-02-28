/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

use std::ffi::CStr;
use std::process::Command;
use libc::c_char;

#[no_mangle]
pub extern "C" fn open_url(url: *const c_char) {
    let url = unsafe {
        assert!(
            !url.is_null(),
            "url must not be null"
        );
        CStr::from_ptr(url)
    }.to_str().unwrap();

    if cfg!(target_os = "windows") {
        Command::new("cmd")
            .arg("/C")
            .arg("start")
            .arg(url)
            .spawn()
            .expect("failed to execute process");
        return;
    }

    if cfg!(target_os = "macos") {
        Command::new("open")
            .arg(url)
            .spawn()
            .expect("failed to execute process");
        return;
    }

    Command::new("sh")
        .arg("-c")
        .arg("xdg-open")
        .arg(url)
        .spawn()
        .expect("failed to execute process");
}