import { test, expect } from '@playwright/test';

test('admin can log in and see application list', async ({ page }) => {
  await page.goto('/');

  await page.getByRole('button', { name: /admin login/i }).click();

  await page.getByLabel(/username/i).fill('admin');
  await page.getByLabel(/password/i).fill('admin123');

  await page.getByRole('button', { name: /sign in/i }).click();

  await expect(
    page.getByRole('heading', { name: /client applications/i }),
  ).toBeVisible();
});
