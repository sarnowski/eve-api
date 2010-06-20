package org.onsteroids.eve.api.provider.corp;

import com.eveonline.api.ApiListResult;
import com.eveonline.api.corp.StarbaseDetail;
import com.eveonline.api.exceptions.ApiException;
import org.onsteroids.eve.api.DateUtility;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.SerializableApiListResult;
import org.onsteroids.eve.api.provider.SerializableApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.Date;

/**
 * @author Tobias Sarnowski
 */
public final class DefaultStarbaseDetail extends SerializableApiResult implements StarbaseDetail {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultStarbaseDetail.class);

	private int state;
	private Date stateTime;
	private Date onlineTime;
	private GeneralSettings generalSettings;
	private CombatSettings combatSettings;
	private ApiListResult<? extends Fuel> fuels;

	@Override
	public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
		XmlUtility xml = new XmlUtility(xmlResult);

		state = Integer.parseInt(xml.getContentOf("state"));
		stateTime = DateUtility.parse(xml.getContentOf("stateTimestamp"), xmlApiResult.getTimeDifference());
		onlineTime = DateUtility.parse(xml.getContentOf("onlineTimestamp"), xmlApiResult.getTimeDifference());
		generalSettings = new DefaultGeneralSettings(xmlApiResult, xml.getNodeByName("generalSettings"));
		combatSettings = new DefaultCombatSettings(xmlApiResult, xml.getNodeByName("combatSettings"));
		fuels = new SerializableApiListResult<DefaultStarbaseDetail.DefaultFuel>(xmlApiResult, xmlResult) {
                @Override
                public Class<? extends DefaultFuel> getRowDefinition() {
                    return DefaultStarbaseDetail.DefaultFuel.class;
                }
		};
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public Date getStateTime() {
		return stateTime;
	}

	@Override
	public Date getOnlineTime() {
		return onlineTime;
	}

	@Override
	public GeneralSettings getGeneralSettings() {
		return generalSettings;
	}

	@Override
	public CombatSettings getCombatSettings() {
		return combatSettings;
	}

	@Override
	public ApiListResult<? extends Fuel> getFuels() {
		return fuels;
	}


	public static final class DefaultGeneralSettings extends SerializableApiResult implements StarbaseDetail.GeneralSettings {

		private int usageFlags;
		private int deployFlags;
		private boolean allowCorporationMembers;
		private boolean allowAllianceMembers;

		public DefaultGeneralSettings(XmlApiResult xmlApiResult, Node node) throws ApiException {
			super(xmlApiResult, node);
		}

		@Override
		public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
			XmlUtility xml = new XmlUtility(xmlResult);

			usageFlags = Integer.parseInt(xml.getContentOf("usageFlags"));
			deployFlags = Integer.parseInt(xml.getContentOf("deployFlags"));
			allowCorporationMembers = "1".equals(xml.getContentOf("allowCorporationMembers"));
			allowAllianceMembers = "1".equals(xml.getContentOf("allowAllianceMembers"));
		}

		@Override
		public int getUsageFlags() {
			return usageFlags;
		}

		@Override
		public int getDeployFlags() {
			return deployFlags;
		}

		@Override
		public boolean allowCorporationMembers() {
			return allowCorporationMembers;
		}

		@Override
		public boolean allowAllianceMembers() {
			return allowAllianceMembers;
		}
	}

	public static final class DefaultCombatSettings extends SerializableApiResult implements StarbaseDetail.CombatSettings {

		private float useStandingsFromOwner;
		private int onStandingDrop;
		private int onStatusDrop;
		private boolean isOnStatusDrop;
		private boolean isOnAggression;
		private boolean isOnCorporationWar;

		public DefaultCombatSettings(XmlApiResult xmlApiResult, Node nodeByName) throws ApiException {
			super(xmlApiResult, nodeByName);
		}

		@Override
		public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
			XmlUtility xml = new XmlUtility(xmlResult);

			useStandingsFromOwner = Float.parseFloat(XmlUtility.getAttribute("ownerID", xml.getNodeByName("useStandingsFrom")));
			onStandingDrop = Integer.parseInt(XmlUtility.getAttribute("standing", xml.getNodeByName("onStandingDrop")));
			onStatusDrop = Integer.parseInt(XmlUtility.getAttribute("standing", xml.getNodeByName("onStatusDrop")));
			isOnStatusDrop = "1".equals(XmlUtility.getAttribute("enabled", xml.getNodeByName("onStatusDrop")));
			isOnAggression = "1".equals(XmlUtility.getAttribute("enabled", xml.getNodeByName("onAggression")));
			isOnCorporationWar = "1".equals(XmlUtility.getAttribute("enabled", xml.getNodeByName("onCorporationWar")));
		}

		@Override
		public float getUseStandingsFromOwner() {
			return useStandingsFromOwner;
		}

		@Override
		public int getOnStandingDrop() {
			return onStandingDrop;
		}

		@Override
		public boolean isOnStatusDrop() {
			return isOnStatusDrop;
		}

		@Override
		public int getOnStatusDrop() {
			return onStatusDrop;
		}

		@Override
		public boolean isOnAggression() {
			return isOnAggression;
		}

		@Override
		public boolean isOnCorporationWar() {
			return isOnCorporationWar;
		}
	}

	public static final class DefaultFuel extends SerializableApiResult implements StarbaseDetail.Fuel {

		private long typeID;
		private long quantity;

		@Override
		public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
			XmlUtility xml = new XmlUtility(xmlResult);

			typeID = Long.parseLong(xml.getAttribute("typeID"));
			quantity = Long.parseLong(xml.getAttribute("quantity"));
		}

		@Override
		public long getTypeId() {
			return typeID;
		}

		@Override
		public long getQuantity() {
			return quantity;
		}
	}
}
