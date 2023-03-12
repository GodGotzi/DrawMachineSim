/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.data;

import net.gotzi.drawmachine.api.Loadable;
import net.gotzi.drawmachine.api.Process;

public abstract class Loader<T> extends Process<T> implements Loadable {
}
