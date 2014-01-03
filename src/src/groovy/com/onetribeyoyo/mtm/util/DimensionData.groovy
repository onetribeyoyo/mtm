package com.onetribeyoyo.mtm.util

class DimensionData {

    static final Map RELEASE = [
        name: "release",
        elements: ["r0.1", "r0.2", "r0.3"],
        colour: null, // no colour means the odd/even stripping will be used
        layoutStyle: LayoutStyle.FLOW,
        primaryYAxis: true
    ]

    static final Map STATUS = [
        name: "status",
        elements: ["on deck":       "Gainsboro",
                   "blocked":       "Tomato",
                   "in progress":   "LightGoldenrod3",
                   "ready to test": "PaleTurquoise",
                   "done":          "DarkSeaGreen1"],
        colour: null, // no colour means the odd/even stripping will be used
        layoutStyle: LayoutStyle.FLOW,
        colourDimension: true,
        primaryXAxis: true
    ]

    static final Map ASSIGNED_TO = [
        name: "assigned to",
        elements: ["you", "me", "them", "everybody"],
        colour: "goldenrod",
        layoutStyle: LayoutStyle.FLOW,
        highlightDimension: true
    ]

    static final Map BUG = [
        name: "bugs",
        elements: ["fatal", "critical", "important", "low", "trivial"],
        colour: "goldenrod",
        layoutStyle: LayoutStyle.FLOW
    ]

    static final Map FEATURE = [
        name: "feature",
        elements: ["feature 1", "feature 2", "feature 3"],
        colour: "SteelBlue",
        layoutStyle: LayoutStyle.FLOW
    ]

    static final Map STRATEGY = [
        name: "strategy",
        elements: ["strengths", "weaknesses", "opportunities", "threats"],
        colour: "SteelBlue",
        layoutStyle: LayoutStyle.FLOW
    ]

}
