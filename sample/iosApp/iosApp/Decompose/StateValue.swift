//
//  StateValue.swift
//  iosApp
//
//  Created by Mikita Kurhanovich on 15/09/2025.
//


import Foundation

import SwiftUI
import Shared

@propertyWrapper struct StateValue<T : AnyObject>: DynamicProperty {
    @ObservedObject
    private var obj: ObservableValue<T>

    var wrappedValue: T { obj.value }

    init(_ value: Value<T>) {
        obj = ObservableValue(value)
    }
}