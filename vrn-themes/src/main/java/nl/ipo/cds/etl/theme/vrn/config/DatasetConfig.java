package nl.ipo.cds.etl.theme.vrn.config;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.postvalidation.H2GeometryStore;
import nl.ipo.cds.etl.postvalidation.IGeometryStore;
import nl.ipo.cds.etl.theme.ThemeConfigException;
import nl.ipo.cds.etl.theme.vrn.VrnThemeConfig;
import nl.ipo.cds.etl.theme.vrn.domain.*;
import nl.ipo.cds.etl.theme.vrn.validation.*;
import nl.ipo.cds.validation.execute.CompilerException;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Configuration(value = "vrn.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public VrnThemeConfig<LandelijkGebiedBeheer> landelijkGebiedBeheerThemeConfig(
			final LandelijkGebiedBeheerValidator validator, final OperationDiscoverer operationDiscoverer)
			throws ThemeConfigException {
		return new VrnThemeConfig<LandelijkGebiedBeheer>(validator, operationDiscoverer, LandelijkGebiedBeheer.class,
				"GebiedBeheer-mapping.json", true);
	}

	@Bean
	@Inject
	public VrnThemeConfig<ProvinciaalGebiedBeheer> provinciaalGebiedBeheerThemeConfig(
			final ProvinciaalGebiedBeheerValidator validator, final OperationDiscoverer operationDiscoverer)
			throws ThemeConfigException {
		return new VrnThemeConfig<ProvinciaalGebiedBeheer>(validator, operationDiscoverer,
				ProvinciaalGebiedBeheer.class, "GebiedBeheer-mapping.json", false);
	}

	@Bean
	@Inject
	public VrnThemeConfig<ProvinciaalGebiedInrichting> provinciaalGebiedInrichtingThemeConfig(
			final ProvinciaalGebiedInrichtingValidator validator, final OperationDiscoverer operationDiscoverer)
			throws ThemeConfigException {
		return new VrnThemeConfig<ProvinciaalGebiedInrichting>(validator, operationDiscoverer,
				ProvinciaalGebiedInrichting.class, "GebiedInrichting-mapping.json", false);
	}

	@Bean
	@Inject
	public VrnThemeConfig<LandelijkGebiedInrichting> landelijkGebiedInrichtingThemeConfig(
			final LandelijkGebiedInrichtingValidator validator, final OperationDiscoverer operationDiscoverer)
			throws ThemeConfigException {
		return new VrnThemeConfig<LandelijkGebiedInrichting>(validator, operationDiscoverer,
				LandelijkGebiedInrichting.class, "GebiedInrichting-mapping.json", true);
	}

	@Bean
	@Inject
	public VrnThemeConfig<LandelijkGebiedVerwerving> landelijkGebiedVerwervingThemeConfig(
			final LandelijkGebiedVerwervingValidator validator, final OperationDiscoverer operationDiscoverer)
			throws ThemeConfigException {
		return new VrnThemeConfig<LandelijkGebiedVerwerving>(validator, operationDiscoverer,
				LandelijkGebiedVerwerving.class, "GebiedVerwerving-mapping.json", true);
	}

	@Bean
	@Inject
	public VrnThemeConfig<ProvinciaalGebiedVerwerving> provinciaalGebiedVerwervingThemeConfig(
			final ProvinciaalGebiedVerwervingValidator validator, final OperationDiscoverer operationDiscoverer)
			throws ThemeConfigException {
		return new VrnThemeConfig<ProvinciaalGebiedVerwerving>(validator, operationDiscoverer,
				ProvinciaalGebiedVerwerving.class, "GebiedVerwerving-mapping.json", false);
	}

	@Configuration(value = "vrn.Validators")
	public static class Validators {
		@Bean
		@Inject
		public ProvinciaalGebiedBeheerValidator provinciaalGebiedBeheerValidator(
				final @Named("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			return new ProvinciaalGebiedBeheerValidator(validatorMessages);
		}

		@Bean
		@Inject
		public LandelijkGebiedBeheerValidator landelijkGebiedBeheerValidator(
				final @Named("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			return new LandelijkGebiedBeheerValidator(validatorMessages);
		}

		@Bean
		@Inject
		public ProvinciaalGebiedInrichtingValidator provinciaalGebiedInrichtingValidator(
				final @Named("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			return new ProvinciaalGebiedInrichtingValidator(validatorMessages);
		}

		@Bean
		@Inject
		public LandelijkGebiedInrichtingValidator landelijkGebiedInrichtingValidator(
				final @Named("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			return new LandelijkGebiedInrichtingValidator(validatorMessages);
		}

		@Bean
		@Inject
		public LandelijkGebiedVerwervingValidator landelijkGebiedVerwervingValidator(
				final @Named("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			return new LandelijkGebiedVerwervingValidator(validatorMessages);
		}

		@Bean
		@Inject
		public ProvinciaalGebiedVerwervingValidator provinciaalGebiedVerwervingValidator(
				final @Named("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			return new ProvinciaalGebiedVerwervingValidator(validatorMessages);
		}
	}

	@Configuration(value = "vrn.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean VrnValidationMessages() {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean();
			properties.setLocation(new ClassPathResource("nl/ipo/cds/etl/theme/vrn/validator.messages"));
			return properties;
		}
	}

	@Bean
	public IGeometryStore vrnGeometryStore () {
		return new H2GeometryStore();
	}

}
