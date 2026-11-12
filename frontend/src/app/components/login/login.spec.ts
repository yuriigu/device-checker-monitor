import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { LoginComponent } from './login';

describe('LoginComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [provideHttpClient(), provideRouter([])],
    }).compileComponents();
  });

  it('requires both login and password before submission', async () => {
    const fixture = TestBed.createComponent(LoginComponent);
    await fixture.whenStable();

    expect(fixture.componentInstance.loginForm.invalid).toBe(true);
    expect(fixture.componentInstance.loginForm.get('login')?.hasError('required')).toBe(true);
    expect(fixture.componentInstance.loginForm.get('password')?.hasError('required')).toBe(true);

    fixture.componentInstance.loginForm.setValue({ login: 'user@example.com', password: 'secret' });
    expect(fixture.componentInstance.loginForm.valid).toBe(true);

    fixture.detectChanges();
    expect((fixture.nativeElement as HTMLElement).querySelector('button[type="submit"]')?.hasAttribute('disabled')).toBe(false);
  });
});
