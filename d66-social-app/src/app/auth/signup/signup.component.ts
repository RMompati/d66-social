import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { matchValidator } from 'src/app/validators/form-validators';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm!: FormGroup;

  constructor() { }

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

  getField(field: string) {
    return this.signupForm.get(field)!;
  }

  isInvalid(field: string): boolean {
    const thisField = this.getField(field);
    return !thisField.valid && thisField.touched
  }

  isValid(field: string): boolean {
    const thisField = this.getField(field);
    return thisField.valid && thisField.touched
  }

  fieldClass(field: string, match: boolean = false): string {
    var isInvalid = this.isInvalid(field);

    if (match) {
      isInvalid = this.fieldHasError(field) || isInvalid;
    }
    return isInvalid ? 'is-invalid' : this.isValid(field) ? 'is-valid': '';
  }

  fieldHasError(field: string): boolean {
    return  this.getField(field).hasError('mismatch')
  }
}
