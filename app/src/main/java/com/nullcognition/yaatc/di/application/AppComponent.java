package com.nullcognition.yaatc.di.application;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.di.activity.ActivityComponent;
import com.nullcognition.yaatc.di.activity.ActivityModule;
import com.nullcognition.yaatc.di.application.database.DatabaseModule;
import com.nullcognition.yaatc.di.application.navigation.NavigationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class, NavigationModule.class, DatabaseModule.class }) public interface AppComponent{

	ActivityComponent plus(ActivityModule mainActivityModule);

}
