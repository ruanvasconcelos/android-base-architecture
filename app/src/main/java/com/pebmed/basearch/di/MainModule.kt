package com.pebmed.basearch.di

import br.com.pebmed.data.di.dataModule
import br.com.pebmed.domain.di.domainModule
import com.pebmed.basearch.presentation.di.presentationModule
import com.pebmed.platform.di.platformModule

val mainModule = dataModule + domainModule + presentationModule + platformModule