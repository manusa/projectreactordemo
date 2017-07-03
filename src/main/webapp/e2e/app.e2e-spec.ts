import { ProjectreactordemoPage } from './app.po';

describe('projectreactordemo App', () => {
  let page: ProjectreactordemoPage;

  beforeEach(() => {
    page = new ProjectreactordemoPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
