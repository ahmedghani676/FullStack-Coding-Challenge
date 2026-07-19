import { Component } from '@angular/core';

import { WorkationTableComponent } from './features/workation-table/workation-table.component';

@Component({
  selector: 'app-root',
  imports: [WorkationTableComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {}
