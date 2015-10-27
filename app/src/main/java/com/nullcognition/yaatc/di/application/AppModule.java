package com.nullcognition.yaatc.di.application;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import dagger.Module;
import dagger.Provides;

@Module public class AppModule{

	private YAATCApp application;

	public AppModule(YAATCApp a){application = a;}

	@Provides public YAATCApp provideYAATCApp(){return application;}

}
