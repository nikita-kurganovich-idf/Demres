//
//  ObservableValue.swift
//  iosApp
//
//  Created by Mikita Kurhanovich on 15/09/2025.
//


import SwiftUI
import Shared

public class ObservableValue<T : AnyObject> : ObservableObject {
    @Published
    var value: T

    private var cancellation: Cancellation?

    init(_ value: Value<T>) {
        self.value = value.value
        self.cancellation = value.subscribe { [weak self] value in self?.value = value }
    }

    deinit {
        cancellation?.cancel()
    }
}