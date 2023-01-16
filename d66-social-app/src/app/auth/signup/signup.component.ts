import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessageService } from 'src/app/message/flash-message.service';
import { matchValidator } from 'src/app/validators/form-validators';
import { D66Response } from '../response.payload';
import { AuthService } from '../shared/auth.service';
import { SignupRequestPayload, emptyPayload } from './singup.request.payload';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit, OnDestroy {

  signupRequestPayload: SignupRequestPayload;
  signupForm!: FormGroup;

  constructor(private router: Router, private authService: AuthService,
    private flashMessageService: FlashMessageService) {
    this.signupRequestPayload = emptyPayload;
  }
  ngOnDestroy(): void {
    this.flashMessageService.reset();
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      confirmEmail: new FormControl('', [Validators.required, Validators.email,
          matchValidator('email', 'confirmEmail')]),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  signup() {
    this.signupRequestPayload.firstName = this.getFieldValue('firstName');
    this.signupRequestPayload.lastName = this.getFieldValue('lastName');
    this.signupRequestPayload.email = this.getFieldValue('email');
    this.signupRequestPayload.username = this.getFieldValue('username');
    this.signupRequestPayload.password = this.getFieldValue('password');

    const singupObserver = {
      next: (data: D66Response) => {
        console.log(data);
        this.router.navigate(['/success']); 
      },
      error: (response: any) => {
        console.log("An error occured!");
        this.flashMessageService.setMessageType(response.error.statusCode)
        this.flashMessageService.setMessage(response.error.message)
        console.log(response);
      }
    };

    this.authService.signup(this.signupRequestPayload)
      .subscribe(singupObserver);
  }

  getFieldValue(fieldName: string): string {
    return this.getField(fieldName).value;
  }

  getField(field: string) {
    return this.signupForm.get(field)!;
  }

  isInvalid(field: string): boolean {
    const thisField = this.getField(field);
    return !thisField.valid && thisField.touched;
  }

  isValid(field: string): boolean {
    const thisField = this.getField(field);
    return thisField.valid && thisField.touched;
  }

  fieldClass(field: string, match: boolean = false): string {
    var isInvalid = this.isInvalid(field);

    if (match) {
      isInvalid = this.fieldHasError(field) || isInvalid;
    }
    return isInvalid ? 'is-invalid' : this.isValid(field) ? 'is-valid': '';
  }

  fieldHasError(field: string): boolean {
    return  this.getField(field).hasError('mismatch');
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
}
