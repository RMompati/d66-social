import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FlashMessageService {

  private message!: string;
  private messageType!: number;

  setMessage(message: string) {
    this.message = message;
  }

  getMessage(): string {
    return this.message;
  }

  setMessageType(messageType: number) {
    this.messageType = messageType;
  }

  getMessageType(): number {
    return this.messageType;
  }
}
