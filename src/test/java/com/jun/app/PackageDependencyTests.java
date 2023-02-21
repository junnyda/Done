package com.jun.app;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = App.class)
public class PackageDependencyTests {
    public static final String STUDY = "..modules.study..";
    public static final String EVENT = "..modules.event..";

    @ArchTest
    ArchRule studyPackageRule = classes().that()
            .resideInAPackage(STUDY)
            .should()
            .onlyBeAccessed()
            .byClassesThat()
            .resideInAnyPackage(STUDY, EVENT);

    @Test
    void studyPackageRuleTest() {
        studyPackageRule.check(new ClassFileImporter().importPackagesOf(App.class));
    }
}