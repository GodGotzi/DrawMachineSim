/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/
package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.api.Buildable;
import net.gotzi.drawmachine.api.Process;

public abstract class Builder<T, S> extends Process<T> implements Buildable<S> {
}
