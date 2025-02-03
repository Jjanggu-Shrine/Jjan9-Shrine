package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class LockException extends ConflictException {
  public LockException() {
    super(ErrorCode.DUPLICATE_LOCK);
  }
}
