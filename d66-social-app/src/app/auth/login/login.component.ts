import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FlashMessageService } from 'src/app/message/flash-message.service';
import { D66Response } from '../response.payload';
import { AuthService } from '../shared/auth.service';
import { emptyPayload, LoginRequestPayload } from './login.request.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  
  loginRequestPayload: LoginRequestPayload;
  loginForm!: FormGroup;

  constructor(private authService: AuthService, private flashMessageService: FlashMessageService) {
    this.loginRequestPayload = emptyPayload;
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }
  
  ngOnDestroy(): void {    
      this.flashMessageService.reset();
  }

  login() {
    this.loginRequestPayload.username = this.getFieldValue('username');
    this.loginRequestPayload.password = this.getFieldValue('password');

    this.authService.login(this.loginRequestPayload)
      .subscribe((data: D66Response) => {
        
        if (data.statusCode >= 401) {
          this.flashMessageService.setMessage(data.message);
          this.flashMessageService.setMessageType(data.statusCode);
          this.loginForm.reset();
        }
      });
  }

  isMessage(): boolean {
    return this.flashMessageService.getMessage() !== undefined;
  }

  getMessage(): string {
    return this.flashMessageService.getMessage();
  }

  getMessageClass() {
    const status = this.flashMessageService.getMessageType();
    return status === 200 ? 'alert-success p-1' :
            status >= 400 ? 'alert-danger p-1' : 'alert-info p-1';
  }

  getFieldValue(fieldName: string) {
    return this.getField(fieldName).value;
  }

  getField(field: string) {
    return this.loginForm.get(field)!;
  }

  isInvalid(field: string): boolean {
    const thisField = this.getField(field);
    return !thisField.valid && thisField.touched;
  }

  isValid(field: string): boolean {
    const thisField = this.getField(field);
    return thisField.valid && thisField.touched;
  }

  fieldClass(field: string): string {
    return this.isInvalid(field) ? 'is-invalid' : this.isValid(field) ? 'is-valid': '';
  }

}
