import { AbstractControl, ValidatorFn } from "@angular/forms";

export function matchValidator(matchTo: string, toMatch: string): ValidatorFn {
  return (control: AbstractControl) => {
    
    const firstField = control.parent?.get(matchTo)?.value;
    const secondField = control.parent?.get(toMatch)?.value;    

     if (firstField !== secondField) {
      return {mismatch: true};
     }

    return null;
  }
}