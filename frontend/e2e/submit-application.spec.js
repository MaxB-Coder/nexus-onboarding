import { test, expect } from '@playwright/test';

test('user can submit an application', async ({ page }) => {
  await page.goto('/');

  await page.getByLabel(/company name/i).fill('Nexus Holdings');
  await page.getByLabel(/company number/i).fill('87654321');
  await page.getByLabel(/annual turnover/i).fill('250000');

  await page.getByRole('button', { name: /submit/i }).click();

  await expect(page.getByText(/application submitted/i)).toBeVisible();
});
