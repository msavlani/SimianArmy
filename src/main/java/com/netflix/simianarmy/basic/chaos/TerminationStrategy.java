/*
 *  Copyright 2012 Immobilien Scout GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.simianarmy.basic.chaos;

import java.io.IOException;

import com.vmware.vim25.mo.VirtualMachine;

/**
 * Abstracts the concrete way a VirtualMachine is terminated. Implement this to fit to your infrastructure.
 *
 * @author ingmar.krusch@immobilienscout24.de
 */
public interface TerminationStrategy {
    /**
     * Terminate the given VirtualMachine.
     */
    void terminate(VirtualMachine virtualMachine) throws IOException;
}
