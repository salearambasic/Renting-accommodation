import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list.component';
import { LoginComponent } from './components/login/login.component';
import { AgentListComponent } from './components/agent-list/agent-list.component';
import { AuthGuard } from './guards/auth.guard';
import { HomeComponent } from './components/home/home.component';
import { CategoryListComponent } from './components/category-list/category-list.component';
import { TypeListComponent } from './components/type-list/type-list.component';
import { EditCategoryComponent } from './components/edit-category/edit-category.component';
import { EditTypeComponent } from './components/edit-type/edit-type.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { AdditionalServicesListComponent } from './components/additional-services-list/additional-services-list.component';
import { EditAdditionalServiceComponent } from './components/edit-additional-service/edit-additional-service.component';
import { RecoveryEmailComponent } from './components/recovery-email/recovery-email.component';
import { RecoveryQuestionComponent } from './components/recovery-question/recovery-question.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'agents', component: AgentListComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'categories', canActivate: [AuthGuard], children: [
    { path: '', component: CategoryListComponent },
    { path: ':id/edit', component: EditCategoryComponent }
  ] },
  { path: 'types', canActivate: [AuthGuard], children: [
    { path: '', component: TypeListComponent },
    { path: ':id/edit', component: EditTypeComponent }
  ] },
  { path: 'additional-services', canActivate: [AuthGuard], children: [
    { path: '', component: AdditionalServicesListComponent },
    { path: ':id/edit', component: EditAdditionalServiceComponent }
  ] },
  // { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  // { path: 'recovery', component: RecoveryEmailComponent},
  // { path: 'question', component: RecoveryQuestionComponent},
  { path: 'comments', component: CommentListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
