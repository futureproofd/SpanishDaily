package to.marcus.rxtesting.injection;

/**
 * Created by marcus on 9/2/2015.
 */

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Scope @Retention(RUNTIME)
public @interface Activity {}